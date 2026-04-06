package HighlandsCoffee.model;

import HighlandsCoffee.enums.MembershipTier;

public class Customer {
    private String customerId; // Khóa chính
    private String fullName; 
    private String phoneNumber; 
    private int rewardPoints; 
    private MembershipTier membershipTier; 

    // Constructor rỗng
    public Customer() {
    }

    // Constructor khởi tạo khách hàng mới
    public Customer(String customerId, String fullName, String phoneNumber) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.rewardPoints = 0; // Mặc định điểm ban đầu là 0
        this.membershipTier = MembershipTier.valueOf("DONG"); // Mặc định hạng ban đầu là Đồng
    }

    // --- Getters và Setters ---

    public String getCustomer_id() {
        return customerId;
    }

    public void setCustomer_id(String customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone_number() {
        return phoneNumber;
    }

    public void setPhone_number(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getReward_points() {
        return rewardPoints;
    }

    public void setReward_points(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public MembershipTier getMembership_tier() {
        return membershipTier;
    }

    public void setMembership_tier(MembershipTier membershipTier) {
        this.membershipTier = membershipTier;
    }

    // --- Phương thức nghiệp vụ ---

    public void displayInfo() {
        System.out.println("Mã KH: " + customerId + " | Tên: " + fullName + 
                           " | SĐT: " + phoneNumber + " | Điểm: " + rewardPoints + 
                           " | Hạng: " + membershipTier);
    }
}
