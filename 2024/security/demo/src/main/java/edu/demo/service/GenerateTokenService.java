package edu.demo.service;

import edu.demo.model.ProductJwtToken;
import edu.demo.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GenerateTokenService {

    private final JwtUtils jwtUtils;

    public String generateToken(ProductJwtToken productJwtToken) {
        return jwtUtils.generateToken(productJwtToken.getProducts(), productJwtToken.getOrderId());
    }
}
