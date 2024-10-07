package context;

import entity.Employees;
import entity.Role;

public final class AuthenticationContextManager {
	private static AuthenticationContextManager instance;
	private Employees loggedInEmployee;
	private Role loggedInRole;
	
	private AuthenticationContextManager() {}
	
	public static synchronized AuthenticationContextManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationContextManager();
        }
        return instance;
	}
	
	public AuthenticationContextManager setAuthn(Employees employee) {
		this.loggedInEmployee = employee;
		return this;
	}
	
	public Employees getAuthn() {
		return this.loggedInEmployee;
	}
	
	public AuthenticationContextManager setAuthz(Role role) {
		this.loggedInRole = role;
		return this;
	}
	
	public Role getAuthz() {
		return this.loggedInRole;
	}
	
	public void clearContext() {
		this.loggedInEmployee = null;
		this.loggedInRole = null;
	}
}
