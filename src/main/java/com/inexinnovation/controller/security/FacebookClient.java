package com.inexinnovation.controller.security;

public class FacebookClient {
	private static final String OAUTH_API_KEY = System.getenv("OAUTH_API_KEY");
	private static final String OAUTH_API_CLIENT_SECRET = System
			.getenv("OAUTH_API_CLIENT_SECRET");
	private static final String OAUTH_API_CLIENT_ID = System
			.getenv("OAUTH_API_CLIENT_ID");
	private static final String OAUTH_API_SCOPES = System
			.getenv("OAUTH_API_SCOPES");

	private static final String OAUTH_AUTH_URI = System
			.getenv("OAUTH_AUTH_URI");
	private static final String OAUTH_REDUCT_URI = System
			.getenv("OAUTH_REDUCT_URI");

	private static final String APP_ADMIN_OAUTH_IDENTITIY_ID_LIST = System
			.getenv("APP_ADMIN_OAUTH_IDENTITIY_ID_LIST");
	public static String getOauthAPIKey() {
		return OAUTH_API_KEY;
	}

	public static String getOauthAPISecret() {
		return OAUTH_API_CLIENT_SECRET;
	}

	public static String getUserInfoSessionId() {
		return "qa_session_user_info";
	}

	public static String getFacebookSessionCodeId(String pFacebookAppID) {
		return "fb_" + pFacebookAppID + "_code";
	}

	public static String getFacebookSessionAccessTokenId(String pFacebookAppID) {
		return "fb_" + pFacebookAppID + "_access_token";
	}

	public static String getFacebookSessioUserId(String pFacebookAppID) {
		return "fb_" + pFacebookAppID + "_user_id";
	}

	public static String getLogInRedirectionURI() {
		return OAUTH_AUTH_URI + "/oauth/authorize?client_id="
				+ OAUTH_API_CLIENT_ID + "&display=page&redirect_uri="
				+ OAUTH_REDUCT_URI + "&scope="
				+ OAUTH_API_SCOPES;
	}

	public static String getAuthURI(String pAuthCode) {
		return OAUTH_AUTH_URI + "/oauth/access_token?client_id="
				+ OAUTH_API_CLIENT_ID + "&client_secret="
				+ OAUTH_API_CLIENT_SECRET + "&redirect_uri=" + OAUTH_REDUCT_URI
				+ "&code=" + pAuthCode;
	}

	public static String getUserInfoURI(String pAccessToken) {
		return OAUTH_AUTH_URI + "/v2.2/me?access_token=" + pAccessToken;

	}

	public static String getExtendTokenURI(String pAccessToken) {
		return OAUTH_AUTH_URI
				+ "/oauth/access_token?grant_type=fb_exchange_token&client_id="
				+ OAUTH_API_CLIENT_ID + "&client_secret="
				+ OAUTH_API_CLIENT_SECRET + "&redirect_uri=" + OAUTH_REDUCT_URI
				+ "&code=" + pAccessToken;
	}

	public static String getDebugTokenURI(String pAccessToken) {
		return OAUTH_AUTH_URI + "/debug_token?input_token=" + pAccessToken
				+ "&access_token=" + pAccessToken;

	}

	public static boolean isAppAdminOauthIdentityID(String pId) {
		boolean lResult = false;

		if (APP_ADMIN_OAUTH_IDENTITIY_ID_LIST != null) {
			String[] lList = APP_ADMIN_OAUTH_IDENTITIY_ID_LIST.split(",");
			for (String currentEmail : lList) {
				if (currentEmail.trim().equalsIgnoreCase(pId)) {
					lResult = true;
					break;
				} // if
			} // for
		} // if

		return lResult;
	}
}
