package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.bindings.UnlockAccForm;
import in.ashokit.service.UserMgmtService;

@RestController
public class UnlockAccRestController {

	@Autowired
	private UserMgmtService userMgmtService;

	@PostMapping("/unlock")
	public String unlock(@RequestBody UnlockAccForm unlockAccForm) {
		
		String unlockMsg = userMgmtService.unlock(unlockAccForm);
		
		return unlockMsg;
	}
}
