package ru.home.mydb_bot.service;

import org.springframework.stereotype.Service;
import ru.home.mydb_bot.model.UserProfileData;
import ru.home.mydb_bot.repository.UserProfileMongoRepository;

import java.util.List;

@Service
public class UsersProfileDataService {

    private UserProfileMongoRepository profileMongoRepository;

    public UsersProfileDataService(UserProfileMongoRepository profileMongoRepository){
        this.profileMongoRepository = profileMongoRepository;
    }

    public List<UserProfileData> getAllProfiles(){
        return profileMongoRepository.findAll();
    }

    public void saveUserSubscription(UserProfileData userProfileData){
        profileMongoRepository.save(userProfileData);
    }

    public void deleteUsersProfileData(String name, String surname){
        profileMongoRepository.deleteByNameAndSurname(name,surname);
    }

    public List<UserProfileData> getUserProfileData(String name, String surname){
       return profileMongoRepository.findByNameAndSurname(name,surname);
    }

    public List<UserProfileData> getUserProfileData(String name){
        return profileMongoRepository.findByName(name);
    }

    public List<UserProfileData> getUserProfileData(long chatId){
        return profileMongoRepository.findByChatId(chatId);
    }
}
