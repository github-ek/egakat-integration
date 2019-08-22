package com.egakat.integration.core.files.components.readers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.apache.any23.encoding.TikaEncodingDetector;
import org.springframework.stereotype.Component;

@Component
public class CharsetDetectorFileReader implements Reader {

	@Override
	public String read(Path input) throws IOException {
		Charset charset = null;
		try (InputStream is = new FileInputStream(input.toFile())) {
			charset = guessCharset(is);
		}

		if (charset == null) {
			throw new RuntimeException(
					"No fue posible determinar el juego de caracteres del archivo " + input.getFileName().toString());
		}

		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(input.toFile()), charset)) {
			StringBuilder sb = new StringBuilder();
			int c = 0;
			while ((c = reader.read()) != -1) {
				if (c != 65279 && c != 65533) {
					sb.append((char) c);
				}
			}
			return sb.toString();
		}
	}

	public static Charset guessCharset(InputStream is) throws IOException {
		try {
			return Charset.forName(new TikaEncodingDetector().guessEncoding(is));
		} catch (Exception e) {
			return Charset.forName("UTF-8");
		}
	}
}
