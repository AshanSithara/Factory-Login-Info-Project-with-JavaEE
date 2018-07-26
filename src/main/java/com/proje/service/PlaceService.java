package com.proje.service;

import com.proje.dao.PlaceDao;
import com.proje.dto.PlaceDto;
import com.proje.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {

    @Autowired
    private PlaceDao placeDao;

    @Transactional
    public Place savePlace(PlaceDto placeDto){

        Place place = new Place();

        try {
            place.setPlaceName(placeDto.getPlaceName());

            placeDao.persist(place);
        }catch (Exception e){
            return null;
        }

        return place;
    }

    @Transactional(readOnly = true)
    public List<PlaceDto> findAll(){

        List<PlaceDto> placeDtos = new ArrayList<PlaceDto>();
        try {
            List<Place> places = placeDao.findAllPlaces();

            for(Place place:places){
                PlaceDto placeDto = new PlaceDto(place);
                placeDtos.add(placeDto);
            }

        }catch (Exception e){
            return null;
        }

        return placeDtos;
    }

    @Transactional
    public Place updatePlace(PlaceDto placeDto) {

        Place place;
        try {
            place = placeDao.find(placeDto.getPlaceId());
            place.setPlaceName(placeDto.getPlaceName());
            placeDao.merge(place);

        }catch (Exception e){
            return null;
        }

        return place;
    }

    @Transactional
    public void removePlace(int id) {
        try {
            Place place = placeDao.find(id);
            placeDao.remove(place);

        }catch (Exception e){
            return;
        }
    }

    public String placeErrorControl(PlaceDto placeDto){
        try {
            List<Place> places = placeDao.findAllPlaces();
            for (Place place1 : places){
                if(placeDto.getPlaceName().equalsIgnoreCase(place1.getPlaceName())){
                    return "Bu Yer Daha Önce Eklenmiş";
                }
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
