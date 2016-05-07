package com.cse110.team36.coupletones.GCM.Server;

/**
 * Created by Duc Le on 5/7/2016.
 */
public class TestSendMessage {

    public static void main(String[] args) {

        System.out.println("Sending POST to GCM");

        String apiKey = "AIzaSyCnduESq54RmoStkgClt_W_eF6Ox_WiDwY";
        Content content = createContent();

        Post2Gcm.post(apiKey, content);
    }

    public static Content createContent() {
        Content c = new Content();

        c.addRegId("ea_KQUzNVPk:APA91bF6mjY2Knojq2MZKuVk7oTPx9zhlyAWv6JHidXdaglrqrtMlcSM-ujbm3B95qozku6i67JTxKB6Zlp87hY_yC4i3CL-8h7Vi8mow33lOikgE8E9bcg5fsHnCPBX-h9pPiKP6AZY");
        //c.addRegId("edQ7oSRilEs:APA91bHHSE_ekkpKw3PtYwFINdjJNXvJ12CU_Crat9ewYZomyyDPqe0coOpTx6gGQo4ZV4WoSVqty_qEDRZXAHHY1j3IUCk4dKxut4uCnIwTFyyr7jMYZ6PXd6iFKCrGNxVMxXHgKJ0q");
        c.createData("Working!!", "Ayy BBy want sum fuk?");

        return c;
    }
}
