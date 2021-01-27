package com.example.util;

public class EnumConstant {

	public enum UserType {
		CUSTOMER("Customer"), MANAGER("Manager");

		private String displayText;

		private UserType(String displayText) {
			this.displayText = displayText;
		}

		public String getDisplayText() {
			return displayText;
		}

		public static boolean isCustomer(String userType) {
			return CUSTOMER.toString().equalsIgnoreCase(userType);
		}

		public static boolean isManager(String userType) {
			return MANAGER.toString().equalsIgnoreCase(userType);
		}
	}

	public enum RoleName {
		ROLE_CUSTOMER("Customer"), ROLE_MANAGER("Manager");

		String displayText;

		private RoleName(String displayText) {
			this.displayText = displayText;
		}

		public String getDisplayText() {
			return displayText;
		}
	}
}
