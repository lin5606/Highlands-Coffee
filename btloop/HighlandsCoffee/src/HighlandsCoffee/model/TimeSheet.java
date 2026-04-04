package model;
import java.time.LocalDateTime;
import java.time.Duration;

public class TimeSheet {
    private Shift schedule; // Liên kết với ca làm việc dự kiến đã được phân công
    private LocalDateTime actualCheckIn; // Giờ vào làm thực tế
    private LocalDateTime actualCheckOut; // Giờ tan làm thực tế

    // Constructor rỗng
    public TimeSheet() {
    }

    // Constructor khởi tạo bảng chấm công cho một ca làm việc cụ thể
    public TimeSheet(Shift schedule) {
        this.schedule = schedule;
        this.actualCheckIn = null; // Mặc định ban đầu chưa chấm công
        this.actualCheckOut = null; 
    }

    // --- Getters và Setters ---

    public Shift getSchedule() {
        return schedule;
    }

    public void setSchedule(Shift schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime getActualCheckIn() {
        return actualCheckIn;
    }

    public void setActualCheckIn(LocalDateTime actualCheckIn) {
        this.actualCheckIn = actualCheckIn;
    }

    public LocalDateTime getActualCheckOut() {
        return actualCheckOut;
    }

    public void setActualCheckOut(LocalDateTime actualCheckOut) {
        this.actualCheckOut = actualCheckOut;
    }

    // --- Phương thức nghiệp vụ ---

    // Ghi nhận giờ vào làm
    public void clockIn(LocalDateTime time) {
        this.actualCheckIn = time;
        System.out.println("Đã ghi nhận giờ VÀO làm: " + this.actualCheckIn);
    }

    // Ghi nhận giờ tan làm
    public void clockOut(LocalDateTime time) {
        this.actualCheckOut = time;
        System.out.println("Đã ghi nhận giờ RA làm: " + this.actualCheckOut);
    }

    // Tính tổng số giờ làm việc thực tế trong ca
    public double calculateWorkedHours() {
        if (actualCheckIn != null && actualCheckOut != null) {
            // Tính toán khoảng thời gian giữa giờ vào và giờ ra
            Duration duration = Duration.between(actualCheckIn, actualCheckOut);
            
            // Chuyển đổi tổng số phút sang giờ (Ví dụ: 90 phút = 1.5 giờ)
            return duration.toMinutes() / 60.0;
        } else {
            System.out.println("Lỗi: Chưa đủ thông tin giờ vào/ra để tính thời gian làm việc.");
            return 0.0;
        }
    }
}


