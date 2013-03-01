package com.github.kolorobot.icm.incident;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.kolorobot.icm.account.Account;
import com.github.kolorobot.icm.account.User;

@RunWith(MockitoJUnitRunner.class)
public class GetIncidentTest {
	
	@InjectMocks
	private IncidentService service = new IncidentService();
	
	@Mock
	private IncidentRepository incidentRepository ;
	
	@Test
	public void shouldFindIncidentByIdAndCreatorIdWhenUserIsInRoleUser() {
		// arrange
		Account account = createAccount(Account.ROLE_USER);
		User user = new User(account);
		// act
		service.getIncident(user, 1L);
		// assert
		verify(incidentRepository, times(1)).findOneByIdAndCreatorId(1L, 1L, "OP01");
	}
	
	@Test
	public void shouldFindIncidentByIdAndAssigneeIdOrCreatorIdWhenUserIsInRoleEmployee() {
		// arrange
		Account account = createAccount(Account.ROLE_EMPLOYEE);
		User user = new User(account);
		// act
		service.getIncident(user, 1L);
		// assert
		verify(incidentRepository, times(1)).findOneByIdAndAssigneeIdOrCreatorId(1L, 1L, "OP01");
	}
	
	@Test
	public void shouldFindIncidentByIdWhenUserIsInRoleAdmin() {
		// arrange
		Account account = createAccount(Account.ROLE_ADMIN);
		User user = new User(account);
		// act
		service.getIncident(user, 1L);
		// assert
		verify(incidentRepository, times(1)).findOne(1L, "OP01");
	}

	private Account createAccount(String role) {
		Account account = new Account("name", "email", "password", role);
		account.setId(1L);
		account.setOperatorId("OP01");
		return account;
	}

}
