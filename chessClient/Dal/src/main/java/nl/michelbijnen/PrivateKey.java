package nl.michelbijnen;

class PrivateKey {
    private PrivateKey(){}
    private static PrivateKey instance = new PrivateKey();
    public static PrivateKey getInstance() {
        return instance;
    }

    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
