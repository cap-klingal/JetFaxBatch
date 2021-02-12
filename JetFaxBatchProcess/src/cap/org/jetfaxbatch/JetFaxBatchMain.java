package cap.org.jetfaxbatch;

import cap.org.jetfaxbatch.services.JetFaxService;

public class JetFaxBatchMain {
	
	public static void main(String[] args)  {
		
		JetFaxService service = new JetFaxService();
        service.updatejetFaxFlag();
		System.out.println("Batch Updated Successfully");
		
		
	}
}
