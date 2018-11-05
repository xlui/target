package app.xlui.target.util;

import app.xlui.target.config.Constant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {
	public static String generate(String username, String password) {
		Algorithm algorithm = Algorithm.HMAC256(password);
		return JWT.create()
				.withClaim("username", username)
				.withClaim("createAt", Constant.currentTime())
				.withExpiresAt(new Date(Constant.expire()))
				.sign(algorithm);
	}

	public static void verify(String token, String username, String password) {
		Algorithm algorithm = Algorithm.HMAC256(password);
		JWTVerifier verifier = JWT.require(algorithm)
				.withClaim("username", username)
				.build();
		verifier.verify(token);
	}

	public static String username(String token) {
		DecodedJWT decodedJWT = JWT.decode(token);
		return decodedJWT.getClaim("username").asString();
	}
}
