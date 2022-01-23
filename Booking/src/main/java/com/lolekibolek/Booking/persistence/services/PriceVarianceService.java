package com.lolekibolek.Booking.persistence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lolekibolek.Booking.persistence.entities.PriceVariance;
import com.lolekibolek.Booking.persistence.repositories.PriceVarianceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PriceVarianceService {
	
	@Autowired
	private PriceVarianceRepository priceVarianceRepository;
	
	public PriceVariance findById(int id){
        return priceVarianceRepository.findById(id);
    }
	
	public List<PriceVariance> findAll (){
        return priceVarianceRepository.findAll();
    }
	
	public void deleteById(int id){
        priceVarianceRepository.deleteById(id);
    }
	
	public PriceVariance save(PriceVariance pv){
        return priceVarianceRepository.save(pv);
    }
}
