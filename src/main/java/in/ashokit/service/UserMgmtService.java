package in.ashokit.service;

import java.util.Map;

import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.UnlockAccForm;
import in.ashokit.bindings.UserRegForm;

public interface UserMgmtService {

	String signin(LoginForm loginForm);

	String checkEmail(String email);

	Map<Integer, String> getCountries();

	Map<Integer, String> getStates(Integer countryId);

	Map<Integer, String> getCities(Integer stateId);

	String signup(UserRegForm userRegForm);

	String unlock(UnlockAccForm unlockAccFrom);

	String forgotPwd(String email);
}
