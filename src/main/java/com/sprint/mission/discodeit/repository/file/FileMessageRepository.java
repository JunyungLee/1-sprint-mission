package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class FileMessageRepository implements MessageRepository {

    private  final String filePath;
    public FileMessageRepository(){
        this.filePath = "tmp/Message.ser";
    }
    public FileMessageRepository(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void save(Message message) {
        Map<UUID, Message> messageMap = this.findAll();
        if(messageMap == null) {
            messageMap = new HashMap<>();
        } try (FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);){
            messageMap.put(message.getId(), message);
            oos.writeObject(messageMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Message findById(UUID messageId) {
        Map<UUID, Message> messageMap = findAll();
        Optional<Message> findMessage = messageMap.values().stream().filter(message -> message.getId().equals(messageId))
                .findAny();
        return findMessage.orElseThrow(() -> new NoSuchElementException("MessageId :" + messageId + "를 찾을 수 없습니다"));
    }

    @Override
    public void delete(UUID messageId) {
        Map<UUID, Message> messageMap = this.findAll();
        if(messageMap == null || !messageMap.containsKey(messageId)) {
            throw new NoSuchElementException("MessageId :" + messageId + "를 찾을 수 없습니다.");
        }
        messageMap.remove(messageId);
        try (FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);){
            oos.writeObject(messageMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<UUID, Message> findAll() {
        Map<UUID, Message> messageMap = new HashMap<>();
        try(FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis)) {
            messageMap = (Map<UUID, Message>) ois.readObject();
        } catch (EOFException e) {
            return messageMap;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return messageMap;
    }
}
