package com.ct.maven.SecKill.dto;

/**
 * @author Administrator
 * 封装json结果
 * @param <T>
 */

//所有ajax；请求的返回类型，封装json结果
public class SeckillResult<T> {

		private boolean success;
		
		private T data;
		
		private String error;

		public SeckillResult(boolean success, T data) {
			super();
			this.success = success;
			this.data = data;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public SeckillResult(boolean success, String error) {
			super();
			this.success = success;
			this.error = error;
		}
		
		
}
