import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String hashedLockerPin; // Write-only field

    // Constructor 1: No-arg JavaBean constructor (chains via this)
    public PatientProfile() {
        this(null, null);
    }

    // Constructor 2: Name-only constructor (chains via this)
    public PatientProfile(String name) {
        this(null, name);
    }

    // Constructor 3: Full master constructor
    public PatientProfile(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
        this.hashedLockerPin = null;
    }

    // JavaBean Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    // Write-once setter for patientId
    public void setPatientId(String patientId) {
        if (this.patientId == null) {
            this.patientId = patientId;
        }
        // Silently ignores subsequent calls
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    // True write-only setter for locker PIN with one-way hashing
    public void setLockerPin(String pin) {
        if (pin != null && pin.matches("\\d{4,6}")) {
            this.hashedLockerPin = hashPin(pin);
        }
    }

    private String hashPin(String pin) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pin.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(pin.hashCode());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4 Tests ===");
        System.out.println("Name-only constructor ID: " + new PatientProfile("Arjun Iyer").getPatientId());
        System.out.println("Full constructor ID: " + new PatientProfile("MT2026-0142", "Arjun Iyer").getPatientId());

        PatientProfile p = new PatientProfile();
        p.setPatientId("MT2026-0142");
        p.setPatientId("HACKED-0000"); // Attempt overwrite
        System.out.println("Write-once ID after overwrite attempt: " + p.getPatientId());
    }
}