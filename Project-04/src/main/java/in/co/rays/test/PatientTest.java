package in.co.rays.test;

import in.co.rays.bean.PatientBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.PatientModel;

public class PatientTest {
	
	public static void main(String[] args) throws ApplicationException {
		
		testFindByPk();
	}

	private static void testFindByPk() throws ApplicationException {
		
		PatientModel model = new PatientModel();
		
	     PatientBean bean =   model.findByPk(1);
		
		System.out.println(bean.getDisease());
		
	}

}
