package com.tokioshool.filmotokio.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	public String token;
}
