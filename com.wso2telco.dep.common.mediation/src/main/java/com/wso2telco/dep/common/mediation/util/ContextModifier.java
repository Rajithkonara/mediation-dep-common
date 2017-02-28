package com.wso2telco.dep.common.mediation.util;

import org.apache.synapse.MessageContext;


public class ContextModifier {

	// Thread local variable containing each thread's ID
	private static final ThreadLocal<ContextModifier> threadId =
			new ThreadLocal<ContextModifier>() {
				@Override protected ContextModifier initialValue() {
					return new ContextModifier();
				}
			};


	private ContextModifier() {
	}

	public static   ContextModifier getInstacnce() {

		return threadId.get();
	}

	public synchronized void setError(MessageContext synContext, String messageId, String errorText,
	                                  String errorVariable, String httpStatusCode, String exceptionType) {

		synContext.setProperty("messageId", messageId);
		synContext.setProperty("errorText", errorText);
		synContext.setProperty("errorVariable", errorVariable);
		synContext.setProperty("httpStatusCode", httpStatusCode);
		synContext.setProperty("exceptionType", exceptionType);
	}
	/**
	 * Error set to context with the row input value
	 *
	 * @param synContext
	 * @param error
	 * @param value
	 */
	public synchronized void setError(MessageContext synContext, ServiceError error, String value) {

		synContext.setProperty("messageId", error.getMessageId());
		synContext.setProperty("errorText", error.getErrorText());
		synContext.setProperty("errorVariable", error.getErrorVariable()+value);
		synContext.setProperty("httpStatusCode", error.getHttpStatusCode());
		synContext.setProperty("exceptionType", error.getExceptionType());
	}
	/**
	 * if internal server error occurred
	 * @param synContext
	 * @param error
	 */
	public synchronized void setError(MessageContext synContext, ServiceError error) {

		synContext.setProperty("messageId", error.getMessageId());
		synContext.setProperty("errorText", error.getErrorText());
		synContext.setProperty("errorVariable", error.getErrorVariable());
		synContext.setProperty("httpStatusCode", error.getHttpStatusCode());
		synContext.setProperty("exceptionType", error.getExceptionType());
		synContext.setProperty("INTERNAL_ERROR", "true");
	}
}
