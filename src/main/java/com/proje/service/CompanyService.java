package com.proje.service;

import com.proje.dao.CompanyDao;
import com.proje.dto.CompanyDto;
import com.proje.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Transactional
    public Company saveCompany(CompanyDto companyDto){

        Company company;
        try{
            company = new Company();
            company.setCompanyName(companyDto.getCompanyName());

            companyDao.persist(company);
        }catch (Exception e){
            return null;
        }
        return company;
    }

    @Transactional(readOnly = true)
    public List<CompanyDto> findAll(){

        List<CompanyDto> companyDtos;
        try {
            companyDtos = new ArrayList<CompanyDto>();
            List<Company> companies = companyDao.findAllComponies();

            for(Company company:companies){
                companyDtos.add(new CompanyDto(company));
            }
        }catch (Exception e){
            return null;
        }
        return companyDtos;
    }

    @Transactional(readOnly = true)
    public CompanyDto findCompanyWithId(int companyId){

        CompanyDto companyDto;
        try {
            Company company = companyDao.findCompanyWithId(companyId);
            companyDto = new CompanyDto(company);
        }catch (Exception e){
            return null;
        }
        return companyDto;
    }

    @Transactional
    public Company updateCompany(CompanyDto companyDto) {

        Company company;
        try {
            company = companyDao.find(companyDto.getCompanyId());
            company.setCompanyName(companyDto.getCompanyName());
            companyDao.merge(company);
        }catch (Exception e){
            return null;
        }
        return company;
    }

    @Transactional
    public void removeCompany(int id) {

        try {
            Company company = companyDao.find(id);
            companyDao.remove(company);

        }catch (Exception e){
            return;
        }
    }

    public String companyErrorControl(CompanyDto companyDto){

        try {
            List<Company> companies = companyDao.findAllComponies();
            for (Company company : companies){
                if(companyDto.getCompanyName().equalsIgnoreCase(company.getCompanyName())){
                    return "Bu Firma Daha Önce Kaydedilmiş.";
                }
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
