package com.joyin.fyzg.config.encrypt.core;

import com.joyin.fyzg.config.encrypt.utils.StreamUtils;
import lombok.SneakyThrows;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EncryptionRequestWrapper extends HttpServletRequestWrapper {


	private byte[] requestBody = new byte[0];

	private Map<String, String[]> paramMap = new HashMap<>();

	public EncryptionRequestWrapper(HttpServletRequest request) {
		super(request);
		try {
			requestBody = StreamUtils.copyToByteArray(request.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener listener) {

			}
		};
	}

	public String getRequestData() {
		return new String(requestBody);
	}

	@SneakyThrows
	public void setRequestData(String requestData) {
		this.requestBody = requestData.getBytes("utf-8");
	}

	public void setParamMap(Map<String, String[]> paramMap) {
		this.paramMap = paramMap;
	}

	public Map<String, String[]> getParamMap() {
		return paramMap;
	}

	@Override
	public String getParameter(String name) {
		if (paramMap.containsKey(name)) {
			return this.paramMap.get(name)[0];
		}
		return super.getParameter(name);
	}

	@Override
	public String[] getParameterValues(String name) {
		if (paramMap.containsKey(name)) {
			return paramMap.get(name);
		}
		return super.getParameterValues(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<>(paramMap.size());
		paramMap.forEach((k, v) -> map.put(k, getParameterValues(k)));
		return map;
	}
}
