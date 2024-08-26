package com.example.e_commerce.controller;



import com.example.e_commerce.dto.*;
import com.example.e_commerce.exception.CartEmptyException;
import com.example.e_commerce.exception.CustomerNotFoundException;
import com.example.e_commerce.exception.ProductNotFountException;
import com.example.e_commerce.exception.ProductNotInCartException;
import com.example.e_commerce.service.CartLineService;
import com.example.e_commerce.service.CartService;
import com.example.e_commerce.service.OrderService;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartLineService cartLineService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrderService orderService;

    @PostMapping("/addtocart")
    public String addToCart(HttpServletRequest request, @RequestParam("productId") String  productId) throws ProductNotFountException{
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        String tokenUserName = jwtUtil.getUsername(token);
        User user = cartService.findUserByUsername(tokenUserName);
        Customer customer = cartService.findCustomerByUser(user);
        Product product = cartService.findProductById(productId);
        if (product == null) {
            throw new ProductNotFountException(productId);
        }
        if (cartService.findCartByCustomer(customer)==null) {
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cartService.addToCart(cart);
            CartLine cartLine = new CartLine();
            cartLine.setQuantity(1);
            cartLine.setProduct(product);
            cartLine.setPrice(product.getPrice()*cartLine.getQuantity());
            cartLine.setCart(cart);
            cartLineService.addCartLine(cartLine);
        }
        else {
            Cart cart = cartService.findCartByCustomer(customer);
            Optional<CartLine> cartLineOptional = cartLineService.findByCartAndProduct(cart,product);
            if(cartLineOptional.isPresent()){
                CartLine cartLine = cartLineOptional.get();
                cartLine.setQuantity(cartLine.getQuantity()+1);
                cartLine.setPrice(product.getPrice()*cartLine.getQuantity());
                cartLineService.addCartLine(cartLine);
            }
            else {
                CartLine cartLine = new CartLine();
                cartLine.setQuantity(1);
                cartLine.setProduct(product);
                cartLine.setPrice(product.getPrice()*1);
                cartLine.setCart(cart);
                cartLineService.addCartLine(cartLine);
            }

        }
        return "Added to cart";
    }

    @DeleteMapping("/removefromcart")
    public String removeFromCart(HttpServletRequest request,@RequestParam("productId") String productId) throws  ProductNotFountException, CartEmptyException, ProductNotInCartException {
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        String tokenUserName = jwtUtil.getUsername(token);
        User user = cartService.findUserByUsername(tokenUserName);
        Customer customer = cartService.findCustomerByUser(user);
        Product product = cartService.findProductById(productId);
        if (product == null) {
            throw new ProductNotFountException(productId);
        }
        if (cartService.findCartByCustomer(customer)==null) {
            throw new CartEmptyException(user.getUsername());
        }
        Cart cart = cartService.findCartByCustomer(customer);
        Optional<CartLine> cartLineOptional = cartLineService.findByCartAndProduct(cart,product);
        if(cartLineOptional.isPresent()){
            CartLine cartLine = cartLineOptional.get();
            if(cartLine.getQuantity()-1==0){
                cartLineService.removeCartLine(cartLine);
            }
            else {
                cartLine.setQuantity(cartLine.getQuantity()-1);
                cartLineService.addCartLine(cartLine);
            }
        }
        else {
            throw new ProductNotInCartException(productId);
        }
        return "Removed from cart";
    }

    @DeleteMapping("/emptycart")
    public String emptyCart(HttpServletRequest request) throws CustomerNotFoundException, CartEmptyException{
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        String tokenUserName = jwtUtil.getUsername(token);
        User user = cartService.findUserByUsername(tokenUserName);
        Customer customer = cartService.findCustomerByUser(user);
        Cart cart =  cartService.findCartByCustomer(customer);
        if(cart==null){
            return "Cart is empty";
        }
        cartLineService.removeByCart(cart);
        return "Cart cleared";
    }

    @GetMapping("/viewcart")
    public List<CartLine> viewCart(HttpServletRequest request) throws CustomerNotFoundException, CartEmptyException{
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        String tokenUserName = jwtUtil.getUsername(token);
        User user = cartService.findUserByUsername(tokenUserName);
        Customer customer = cartService.findCustomerByUser(user);
        Cart cart =  cartService.findCartByCustomer(customer);
        if (cartLineService.getByCart(cart).isEmpty()){
            throw new CartEmptyException(user.getUsername());
        }
        return cartLineService.getByCart(cart);
    }

    @PostMapping("/checkout")
    public String placeOrder(HttpServletRequest request) throws CartEmptyException, ProductNotFountException {
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        String tokenUserName = jwtUtil.getUsername(token);
        User user = cartService.findUserByUsername(tokenUserName);
        Customer customer = cartService.findCustomerByUser(user);
        Cart cart =  cartService.findCartByCustomer(customer);
        if (cartLineService.getByCart(cart).isEmpty()){
            throw new CartEmptyException(user.getUsername());
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setOrderTotal(cartLineService.sumByPrice(cart));
        List<CartLine> cartLines = cartLineService.getByCart(cart);
        List<OrderLine> orderLines = new ArrayList<>();
        for (CartLine cartLine : cartLines) {
            if (productService.findByProductId(cartLine.getProduct().getProductId()).getQuantity()>cartLine.getQuantity()){
                OrderLine orderLine = new OrderLine();
                orderLine.setOrder(order);
                orderLine.setProduct(cartLine.getProduct());
                orderLine.setQuantity(cartLine.getQuantity());
                orderLine.setPrice(cartLine.getPrice());
                orderLines.add(orderLine);
                Product product = productService.findByProductId(cartLine.getProduct().getProductId());
                product.setQuantity(product.getQuantity()-cartLine.getQuantity());
                productService.save(product);
            }
            else {
                return cartLine.getProduct().getProductId()+" Out of stock";
            }
        }
        order.setOrderLines(orderLines);
        orderService.save(order);

        cartLineService.removeByCart(cart);
        return "Order Placed";
    }

    @GetMapping("/orderhistory")
    public List<Order> orderHistory(HttpServletRequest request)  {
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        String tokenUserName = jwtUtil.getUsername(token);
        User user = cartService.findUserByUsername(tokenUserName);
        Customer customer = cartService.findCustomerByUser(user);
        return orderService.findByCustomer(customer);
    }
}
