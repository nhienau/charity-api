package com.test.charity_api.service.impl;

import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.dto.DonorResponse;
import com.test.charity_api.entity.Donor;
import com.test.charity_api.entity.Role;
import com.test.charity_api.mapper.DonorMapper;
import com.test.charity_api.repository.DonorRepository;
import com.test.charity_api.repository.RoleRepository;
import com.test.charity_api.service.DonorService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DonorServiceImpl implements DonorService {

    @Autowired
    private DonorRepository donorRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DonorServiceImpl(DonorRepository donorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.donorRepository = donorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DonorDTO findByPhoneNumber(String phoneNumber) {
        Donor d = donorRepository.findByStatusTrueAndPhoneNumber(phoneNumber);
        return DonorMapper.mapToDonorDto(d);
    }

    @Override
    public DonorDTO insertDonor(DonorDTO d) {
        Donor temp = DonorMapper.mapToDonor(d);
        temp.setPassword(passwordEncoder.encode(d.getPassword()));
        Optional<Role> r = roleRepository.findByName("USER");
        temp.setRoles(Collections.singletonList(r.orElse(null)));
        Donor saved = donorRepository.save(temp);
        return DonorMapper.mapToDonorDto(saved);
    }

    @Override
    public DonorDTO findById(String id) {
        Donor d = donorRepository.findByStatusTrueAndId(id);
        if (d != null && !d.isStatus()) {
            return null;
        }
        return DonorMapper.mapToDonorDto(d, true, false);
    }

    @Override
    public void updatePassword(String username, String newPassword) throws Exception {
        donorRepository.updatePassword(username, newPassword);
    }

    @Override
    public DonorDTO FindUser(String id) {
        Donor temp = donorRepository.FindUser(id);
        return DonorMapper.mapToDonorDto(temp, true, true);
    }

    @Override
    public void createDonor(DonorDTO req) {
        DonorDTO temp = new DonorDTO();
        temp.setId(req.getId());
        temp.setPassword(req.getPassword());
        temp.setPhoneNumber(req.getPhoneNumber());
        temp.setDefaultName(req.getDefaultName());
        temp.setRole(req.getRole());
        temp.setStatus(true);
        Donor d = new Donor();
        try {
            d = DonorMapper.mapToDonor(temp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        donorRepository.save(d);
    }

    @Override
    public void updateDonor(DonorDTO req) {
        Donor d = DonorMapper.mapToDonor(req);
        donorRepository.save(d);
    }

    @Override
    public Donor deleteDonor(String id) {
        int updated = donorRepository.deleteDonor(id);
        if (updated > 0) {
            return donorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Donor not found"));
        }
        throw new EntityNotFoundException("Donor not found");
    }

    @Override
    public DonorResponse getDonors(int pageNo, int pageSize, String query, String filter) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Donor> result = donorRepository.getDonors(query, filter, pageable);
        List<DonorDTO> content = result.getContent().stream()
                .map(l -> DonorMapper.mapToDonorDto(l))
                .collect(Collectors.toList());

        DonorResponse response = new DonorResponse();
        response.setContent(content);
        response.setPageNo(pageNo + 1);
        response.setPageSize(pageSize);
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());
        response.setLast(result.isLast());
        return response;
    }

    @Override
    public List<DonorDTO> getAll() {
        List<Donor> donors = (ArrayList) donorRepository.findAll();
        return donors.stream().map(l -> DonorMapper.mapToDonorDto(l)).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        return donorRepository.existsById(id);
    }

}
