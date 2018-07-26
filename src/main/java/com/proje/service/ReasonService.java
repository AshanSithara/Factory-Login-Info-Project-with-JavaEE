package com.proje.service;

import com.proje.dao.ReasonDao;
import com.proje.dto.IntroductionReasonDto;
import com.proje.model.IntroductionReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReasonService {

    @Autowired
    private ReasonDao reasonDao;

    @Transactional
    public IntroductionReason saveReason(IntroductionReasonDto reasonDto){

        IntroductionReason reason;
        try {
            reason = new IntroductionReason();
            reason.setReasonName(reasonDto.getReasonName());

            reasonDao.persist(reason);

        }catch (Exception e){
            return null;
        }

        return reason;
    }

    @Transactional(readOnly = true)
    public List<IntroductionReasonDto> findAll(){

        List<IntroductionReasonDto> reasonDtos = new ArrayList<IntroductionReasonDto>();
        try {
            List<IntroductionReason> reasons = reasonDao.findAllReasons();

            for(IntroductionReason reason:reasons){
                reasonDtos.add(new IntroductionReasonDto(reason));
            }

        }catch (Exception e){
            return null;
        }

        return reasonDtos;
    }

    @Transactional
    public IntroductionReason updateReason(IntroductionReasonDto reasonDto) {

        IntroductionReason reason;
        try{
            reason = reasonDao.find(reasonDto.getReasonId());
            reason.setReasonName(reasonDto.getReasonName());
            reasonDao.merge(reason);

        }catch (Exception e){
            return null;
        }

        return reason;
    }

    @Transactional
    public void removeReason(int id) {
        try {
            IntroductionReason reason = reasonDao.find(id);
            reasonDao.remove(reason);

        }catch (Exception e){
            return;
        }
    }

    public String reasonErrorControl(IntroductionReasonDto reasonDto){
        try {
            List<IntroductionReason> reasons = reasonDao.findAllReasons();
            for (IntroductionReason reason1 : reasons){
                if(reasonDto.getReasonName().equalsIgnoreCase(reason1.getReasonName())){
                    return null;
                }
            }

        }catch (Exception e){
            return null;
        }
        return null;
    }
}
