import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JavaBeanLibraryMember {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String hashedSecurityAnswer; // Write-only property

    // Constructor 1: No-arg JavaBean constructor (chains via this)
    public JavaBeanLibraryMember() {
        this(null, null);
    }

    // Constructor 2: Name-only constructor (chains via this)
    public JavaBeanLibraryMember(String name) {
        this(null, name);
    }

    // Constructor 3: Full master constructor
    public JavaBeanLibraryMember(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
        this.hashedSecurityAnswer = null;
    }

    // JavaBean Getters and Setters
    public String getMembershipId() {
        return membershipId;
    }

    // Write-once setter for membershipId
    public void setMembershipId(String id) {
        if (this.membershipId == null) {
            this.membershipId = id;
        }
        // Silently ignores subsequent calls
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Write-only setter applying a deterministic one-way hash
    public void setSecurityAnswer(String answer) {
        if (answer != null && !answer.trim().isEmpty()) {
            this.hashedSecurityAnswer = hashAnswer(answer.trim().toLowerCase());
        }
    }

    private String hashAnswer(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(input.hashCode());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4 Tests ===");
        System.out.println("Name-only constructor ID: " + new JavaBeanLibraryMember("Priya Nair").getMembershipId());
        System.out.println("Full constructor ID: " + new JavaBeanLibraryMember("LIB-8841", "Priya Nair").getMembershipId());

        JavaBeanLibraryMember m = new JavaBeanLibraryMember();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000"); // Overwrite attempt
        System.out.println("Write-once ID after overwrite attempt: " + m.getMembershipId());
    }
}