package com.test.test.services;

import com.test.test.dto.UserDto;
import com.test.test.entities.User;
import com.test.test.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {

        var it = userRepository.findAll();

        var users = new ArrayList<User>();
        it.forEach(e -> users.add(e));

        return users;
    }

    public Long count() {

        return userRepository.count();
    }

    public void deleteById(Long userId) {

        userRepository.deleteById(userId);
    }

    public void addUser(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String login(UserDto userDto){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String userName = userDto.getUserName();
        User thisUser = userRepository.findByUserName(userName);
        if(thisUser == null)
            return "Your user name or password is not correct";
        if (encoder.matches(userDto.getPassword(),thisUser.getPassword())){
            String jwtToken = createJWT(String.valueOf(thisUser.getId()),thisUser.getUserName(),3600000);
            return jwtToken;
        }

        return "Your user name or password is not correct";
    }

    public static String createJWT(String id, String issuer, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        String SECRET_KEY = "Aileen";
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                //set start time
                .setIssuedAt(now)
                //set body
                .setIssuer(issuer)
                //encript
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {
        String SECRET_KEY = "Aileen";
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

     //validate token
    public Boolean validateToken(String userNameInToken, String idInToken, String jwtToken) {

        User findUser = userRepository.findByUserName(userNameInToken);
        final String userId = String.valueOf(findUser.getId());
        return (userId.equals(idInToken) && !isTokenExpired(jwtToken));
    }

    private Boolean isTokenExpired(String jwtToken) {
        Claims claims = decodeJWT(jwtToken);
        final Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

}
