package br.com.cep.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.cep.api.controller.AddressControllerTest;
import br.com.cep.api.controller.CepControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ CepControllerTest.class, AddressControllerTest.class })
public class TestSuite {

}
