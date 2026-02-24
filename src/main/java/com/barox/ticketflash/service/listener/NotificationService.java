package com.barox.ticketflash.service.listener;

import org.springframework.stereotype.Service;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import lombok.extern.slf4j.Slf4j;
import com.barox.ticketflash.event.BookingSuccessEvent;

@Service
@Slf4j
public class NotificationService {

    @EventListener
    @Async
    public void handleBookingSuccess(BookingSuccessEvent event) {
        // Giả lập gửi email tốn 3 giây
        try {
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        log.info("Đã gửi email xác nhận tới: {}", event.getEmail());
        log.info("Nội dung: Bạn đã đặt thành công {} vé cho sự kiện {}", 
                 event.getTotalTicket(), event.getEventName());
    }
}