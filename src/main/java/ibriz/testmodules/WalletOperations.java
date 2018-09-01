package ibriz.testmodules;

import foundation.icon.icx.KeyWallet;

import java.io.File;

public class WalletOperations {
    private static void createWallet(String username) throws Exception {
        KeyWallet wallet = KeyWallet.create();
        File keystorePath = new File(".");
        KeyWallet.store(wallet, "password", keystorePath);
        System.out.println("Wallet created for the username");
    }

    public static void main(String[] args) {
        try {
            createWallet("sagarduwal");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
