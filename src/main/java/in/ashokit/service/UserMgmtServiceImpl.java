package in.ashokit.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.UnlockAccForm;
import in.ashokit.bindings.UserRegForm;
import in.ashokit.entity.CityMasterEntity;
import in.ashokit.entity.CountryMasterEntity;
import in.ashokit.entity.StateMasterEntity;
import in.ashokit.entity.UserDtlsEntity;
import in.ashokit.repository.CityMasterRepo;
import in.ashokit.repository.CountryMasterRepo;
import in.ashokit.repository.StateMasterRepo;
import in.ashokit.repository.UserDtlsRepo;
import in.ashokit.util.EmailUtility;
import in.ashokit.util.RandomPasswordGenerator;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	UserDtlsRepo userDtlsRepo;

	@Autowired
	CountryMasterRepo countryRepo;

	@Autowired
	StateMasterRepo stateRepo;

	@Autowired
	CityMasterRepo cityRepo;

	@Autowired
	EmailUtility emailUtility;

	@Override
	public String signin(LoginForm loginForm) {

		UserDtlsEntity user = userDtlsRepo.findByUserEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());

		if (user == null) {

			return "Invalid Credentials";
		}

		if (user.getAccStatus().equals("locked")) {

			return "Your Account is locked";
		}

		return "SUCCESS";

	}

	@Override
	public String checkEmail(String email) {

		UserDtlsEntity user = userDtlsRepo.findByUserEmail(email);

		if (user != null) {
			return "Duplicate";
		}
		return "Unique";
	}

	@Override
	public Map<Integer, String> getCountries() {

		Map<Integer, String> countryMap = new HashMap<>();

		List<CountryMasterEntity> countriesList = countryRepo.findAll();

		for (CountryMasterEntity country : countriesList) {

			countryMap.put(country.getCountryId(), country.getCountryName());

		}

		return countryMap;

	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {

		Map<Integer, String> stateMap = new HashMap<>();

		List<StateMasterEntity> stateList = stateRepo.findByCountryId(countryId);

		for (StateMasterEntity state : stateList) {

			stateMap.put(state.getStateId(), state.getStateName());

		}

		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {

		Map<Integer, String> cityMap = new HashMap<>();

		List<CityMasterEntity> cityList = cityRepo.findByStateId(stateId);

		for (CityMasterEntity city : cityList) {

			cityMap.put(city.getCityId(), city.getCityName());

		}

		return cityMap;
	}

	@Override
	public String signup(UserRegForm userRegForm) {

		UserDtlsEntity userDtlsEntity = new UserDtlsEntity();

		String tempPwd = RandomPasswordGenerator.getAlphaNumericString();

		BeanUtils.copyProperties(userRegForm, userDtlsEntity);

		userDtlsEntity.setUserPwd(tempPwd);
		userDtlsEntity.setAccStatus("locked");

		boolean isSent = emailUtility.sendEmail(userDtlsEntity.getUserEmail(), "Registration success",
				emailBody("EmailTemplateForRegistration.txt", userDtlsEntity));

		if (isSent == false) {
			return "failed";
		}

//		EmailService emailService = new EmailService();
//		emailService.createEmail(userRegForm.getEmail(), "Registarion Success",
//				EmailBody.emailBody(userRegForm.getFirstName(), userRegForm.getLastName(), tempPwd));
		
		userDtlsRepo.save(userDtlsEntity);
		return "Please check your email to unlock account";

	}

	@Override
	public String unlock(UnlockAccForm unlockAccFrom) {

		if (!unlockAccFrom.getNewPwd().equals(unlockAccFrom.getConfirmNewPwd())) {
			return "New password and confirm password not matched";
		}

		UserDtlsEntity user = userDtlsRepo.findByUserEmailAndUserPwd(unlockAccFrom.getEmail(),
				unlockAccFrom.getTmpPwd());

		if (user == null) {

			return "Temporary password not matched";
		}

		user.setUserPwd(unlockAccFrom.getConfirmNewPwd());
		user.setAccStatus("Active");

		userDtlsRepo.save(user);

		return "Account unlocked, please proceed with login";

	}

	@Override
	public String forgotPwd(String email) {

		UserDtlsEntity user = userDtlsRepo.findByUserEmail(email);

		if (user == null) {
			return "Invalid Email";
		}

		boolean isSent = emailUtility.sendEmail(email, "AshokIt forgot password",
				emailBody("EmailTemplateForForgotPassword.txt", user));

		if (isSent == false) {
			return "failed";
		}
//		EmailService emailService = new EmailService();
//
//		emailService.createEmail(email, "Forgot Password", "This is your Current password : " + user.getUserPwd());

		return "please check you email password sent";

	}

	public String emailBody(String file, UserDtlsEntity user) {

		StringBuffer buffer = new StringBuffer();

		FileReader fileReader = null;
		BufferedReader buffredReader = null;
		String body = null;

		try {

			fileReader = new FileReader(file);
			buffredReader = new BufferedReader(fileReader);

			String line = buffredReader.readLine();

			while (line != null) {
				buffer.append(line);
				line = buffredReader.readLine();
			}

			body = buffer.toString();
			body = body.replace("{FNAME}", user.getFirstName());
			body = body.replace("{LNAME}", user.getLastName());
			body = body.replace("{TEMP-PWD}", user.getUserPwd());
			body = body.replace("{EMAIL}", user.getUserEmail());
			body = body.replace("{PWD}", user.getUserPwd());
			

		} // try
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} // catch
		finally {

			try {
				if (fileReader != null) {
					fileReader.close();
				}

			} // try
			catch (IOException io) {
				io.printStackTrace();
			} // catch

		} // finally

		return body;
	}// emailBody()

}
