-- User 테이블
CREATE TABLE IF NOT EXISTS users
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username   VARCHAR(50)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    profile_id UUID,
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES binary_contents(id) ON DELETE CASCADE
);

-- Channel 테이블
CREATE TABLE IF NOT EXISTS channels
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name       VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

-- Message 테이블
CREATE TABLE IF NOT EXISTS messages
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    channel_id UUID NOT NULL,
    user_id    UUID NOT NULL,
    content    TEXT NOT NULL,
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_channels FOREIGN KEY (channel_id) REFERENCES channels (id) ON DELETE CASCADE,
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

-- ReadStatus 테이블
CREATE TABLE IF NOT EXISTS read_status
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID NOT NULL,
    message_id UUID NOT NULL,
    last_read_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_messages FOREIGN KEY (message_id) REFERENCES messages (id) ON DELETE CASCADE,
    CONSTRAINT uq_user_message UNIQUE (user_id, message_id)
);

-- BinaryContent 테이블 (파일 메타 정보만 저장)
CREATE TABLE IF NOT EXISTS binary_content
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    file_name    VARCHAR(255) NOT NULL,
    size         BIGINT       NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    created_at   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

-- messageAttachments 테이블
CREATE TABLE IF NOT EXISTS message_attachments(
    message_id UUID NOT NULL,
    attachment_id UUID NOT NULL,
    PRIMARY KEY(message_id, attachment_id),
    CONSTRAINT fk_messages FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
    CONSTRAINT fk_attachments FOREIGN KEY (attachment_id) REFERENCES binary_content(id) ON DELETE CASCADE
);

--userStatus 테이블
CREATE TABLE IF NOT EXISTS user_statuses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE,
    last_active_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(id) on DELETE CASCADE
);