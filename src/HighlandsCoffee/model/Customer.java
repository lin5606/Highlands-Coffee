package HighlandsCoffee.model;

import enums.MembershipTier;

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
        this.membershipTier = "Đồng"; // Mặc định hạng ban đầu là Đồng
    }

    // --- Getters và Setters ---

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getMembershipTier() {
        return membershipTier;
    }

    public void setMembershipTier(String membershipTier) {
        this.membershipTier = membershipTier;
    }

    // --- Phương thức nghiệp vụ ---

    public void displayInfo() {
        System.out.println("Mã KH: " + customerId + " | Tên: " + fullName + 
                           " | SĐT: " + phoneNumber + " | Điểm: " + rewardPoints + 
                           " | Hạng: " + membershipTier);
    }
}
