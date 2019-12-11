package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue
    @Column(name = "session_id")
    private Integer sessionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "expire_time")
    private Date expireTime;
}
