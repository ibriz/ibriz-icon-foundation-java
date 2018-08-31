import com.google.gson.Gson;
import foundation.icon.icx.*;
import foundation.icon.icx.data.Address;
import foundation.icon.icx.data.Bytes;
import foundation.icon.icx.transport.http.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.RpcConverter;
import foundation.icon.icx.transport.jsonrpc.RpcItem;
import foundation.icon.icx.transport.jsonrpc.RpcObject;
import foundation.icon.icx.transport.jsonrpc.RpcValue;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.grails.web.json.JSONArray;
import org.grails.web.json.JSONObject;
import org.web3j.crypto.CipherException;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;


public class TestTransaction2 {

    public final String URL = "http://192.168.1.9:9000/api/v3";

    private IconService iconService;
    private Wallet wallet;


    private String scoreAddress = "cx02b13428a8aef265fbaeeb37394d3ae8727f7a19";
    private String toAddStr = "hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31"; // second address where the amount is being transfered


    public TestTransaction2() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        iconService = new IconService(new HttpProvider(httpClient, URL));
        File file = new File("C:\\Users\\morga\\Desktop\\Temp\\ibriz-icon-foundation-java\\UTC--2018-08-24T13-35-41.671000000Z--hx65f6e18d378b57612a28f72acb97021eaa82aa5a.json");
        try {
            System.out.println("before loading wallet");
            wallet = KeyWallet.load("password", file);
            System.out.println("after loading wallet" + wallet.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    public void sendTransaction(String toAddress, String token_id) throws IOException {
        BigInteger networkId = new BigInteger("3");

        long timestamp = System.currentTimeMillis() * 1000L;
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";
        String toAddStr = toAddress; // second address where the amount is being transfered
        RpcObject params = new RpcObject.Builder()
                .put("_to", new RpcValue(toAddStr))
                .put("_tokenId", new RpcValue(token_id))
                .build();

        Transaction transaction = TransactionBuilder.of(networkId)
                .from(wallet.getAddress())
                .to(new Address(scoreAddress))
                .stepLimit(new BigInteger("10000"))
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(new BigInteger("10000"))
                .call("transfer")
                .params(params)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        System.out.println("txHash:" + hash);


        /*
         * GET BALANCE OF CURRENT ACCOUNT
         * */
        getBalance(wallet.getAddress());


        /*
         * GET BALANCE OF THE SPECIFIED ACCOUNT
         * */
        Address specified_address = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
        getBalance(specified_address);


    }

    public void approveTransaction(String toAddress, String token_id) throws IOException {
        BigInteger networkId = new BigInteger("3");

        long timestamp = System.currentTimeMillis() * 1000L;
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";
        String toAddStr = toAddress; // second address where the amount is being transfered
        RpcObject params = new RpcObject.Builder()
                .put("_to", new RpcValue(toAddStr))
                .put("_tokenId", new RpcValue(token_id))
                .build();

        Transaction transaction = TransactionBuilder.of(networkId)
                .from(wallet.getAddress())
                .to(new Address(scoreAddress))
                .stepLimit(new BigInteger("10000"))
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(new BigInteger("10000"))
                .call("approve")
                .params(params)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        System.out.println("txHash:" + hash);
    }

    public void getBalance(Address address) throws IOException {
        BigInteger balance_specified = iconService.getBalance(address).execute();
        System.out.println("balance of address: " + address + ":" + balance_specified);
    }

    public void createToken() throws IOException {
        BigInteger networkId = new BigInteger("3");

        long timestamp = System.currentTimeMillis() * 1000L;
        Address ownerAddress = wallet.getAddress();
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";
        String toAddStr = "hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31"; // second address where the amount is being transfered
        Random r = new Random();
        int number =  r.nextInt((10 - 1) + 1) + 1;
        RpcObject params = new RpcObject.Builder()
                .put("_name", new RpcValue("Sagar"+ number))
                .put("_age", new RpcValue("10"))
                .put("_gender", new RpcValue("M"))
                .put("_ipfs_handle", new RpcValue("1000"+ number + number))
                .build();

        Transaction transaction = TransactionBuilder.of(networkId)
                .from(ownerAddress)
                .to(new Address(scoreAddress))
                .stepLimit(new BigInteger("10000"))
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(new BigInteger("10000"))
                .call("create_idol")
                .params(params)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        System.out.println("txHash:" + hash);
    }

    public void getAllTokensOf(String address) throws IOException {


        Address firstAddress = new Address(address);
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";

        RpcObject params = new RpcObject.Builder()
                .put("_owner", new RpcValue(firstAddress))
                .build();

        Call<RpcItem> call = new Call.Builder()
                .from(firstAddress)
                .to(new Address(scoreAddress))
                .method("get_tokens_of_owner")
                .params(params)
                .build();

        RpcItem result = iconService.call(call).execute();
        System.out.println(firstAddress + " :result:" + result.asString());

        JSONObject obj = new JSONObject(result.asString());
        JSONArray arr = obj.getJSONArray("idols");
        for (Object o : arr) {
            System.out.println("o.toString() = " + o.toString());
            System.out.println(getTokenInfo(o.toString()));
        }
    }

    class Idol {
        String name;
        BigInteger age;
        String gender;
        String ipfs_handle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigInteger getAge() {
            return age;
        }

        public void setAge(BigInteger age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIpfs_handle() {
            return ipfs_handle;
        }

        public void setIpfs_handle(String ipfs_handle) {
            this.ipfs_handle = ipfs_handle;
        }

        public Idol(String name, BigInteger age, String gender, String ipfs_handle) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.ipfs_handle = ipfs_handle;
        }

        @Override
        public String toString() {
            return new JSONObject().put("name", name)
                    .put("age", age)
                    .put("gender", gender)
                    .put("ipfs_handle", ipfs_handle).toString();
        }
    }

    public String getTokenInfo(String tokenId) throws IOException {
        Address firstAddress = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";

        RpcObject params = new RpcObject.Builder()
                .put("_tokenId", new RpcValue(tokenId))
                .build();

        Call<RpcItem> call = new Call.Builder()
                .from(firstAddress)
                .to(new Address(scoreAddress))
                .method("get_idol")
                .params(params)
                .build();

        RpcItem result = iconService.call(call).execute();

        System.out.println(firstAddress + " :result:" + result.asString());
        Idol idol = new Gson().fromJson(result.asString(), Idol.class);
        return String.format("Address (%s) => Name: %s Age: %d Gender: %s ", firstAddress, idol.getName(),
                idol.getAge(), idol.getGender());
    }

    public void getTokenInfoConverter(String tokenId) throws IOException {
        Address firstAddress = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";

        RpcObject params = new RpcObject.Builder()
                .put("_tokenId", new RpcValue(tokenId))
                .build();

        Call<Idol> call = new Call.Builder()
                .from(firstAddress)
                .to(new Address(scoreAddress))
                .method("get_idol")
                .params(params)
                .buildWith(Idol.class);

        iconService.addConverterFactory(new RpcConverter.RpcConverterFactory() {
            @Override
            public RpcConverter create(Class type) {
                if (type.isAssignableFrom(Idol.class)) {
                    return new RpcConverter<Idol>() {
                        @Override
                        public Idol convertTo(RpcItem object) {
                            // Unpacking from RpcItem to the user defined class
                            String name = object.asObject().getItem("name").asString();
                            BigInteger age = object.asObject().getItem("age").asInteger();
                            String gender = object.asObject().getItem("gender").asString();
                            String ipfs_handle = object.asObject().getItem("ipfs_handle").asString();
                            return new Idol(name, age, gender, ipfs_handle);
                        }

                        @Override
                        public RpcItem convertFrom(Idol person) {
                            // Packing from the user defined class to RpcItem
                            return new RpcObject.Builder()
                                    .put("name", new RpcValue(person.name))
                                    .put("age", new RpcValue(person.age))
                                    .put("gender", new RpcValue(person.gender))
                                    .put("ipfs_handle", new RpcValue(person.ipfs_handle))
                                    .build();
                        }
                    };
                }
                return null;
            }
        });
        Idol result = iconService.call(call).execute();

        System.out.println(firstAddress + " :result:" + result.toString());
    }

    public static void main(String[] args) throws IOException {
//        new TestTransaction2().createToken();
//        new TestTransaction2().createToken();
//        new TestTransaction2().createToken();
//        new TestTransaction2().createToken();
//        new TestTransaction2().createToken();

        new TestTransaction2().getAllTokensOf("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");
        new TestTransaction2().getAllTokensOf("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");


//        new TestTransaction2().getAllTokensOf("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");
//        System.out.println("\n=======================================\n");
//        new TestTransaction2().getAllTokensOf("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
//        System.out.println("\n=======================================\n");
//        System.out.println("||| Approving the transaction");
//        new TestTransaction2().approveTransaction("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31", "462f3042-ac4a-11e8-8228-000c29be104e");
//        System.out.println("\n=======================================\n");
//        System.out.println("||| Processing the transaction");
//        new TestTransaction2().sendTransaction("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31", "462f3042-ac4a-11e8-8228-000c29be104e");
//        System.out.println("\n=======================================\n");
//        new TestTransaction2().getAllTokensOf("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");
//        System.out.println("\n=======================================\n");
//        new TestTransaction2().getAllTokensOf("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
//        System.out.println("\n=======================================\n");
//        new TestTransaction2().getTokenInfo("8a13f1fc-ac5f-11e8-8228-000c29be104e");
    }

}
