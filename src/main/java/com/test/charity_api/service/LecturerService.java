
package com.test.charity_api.service;

import com.test.charity_api.dto.LecturerDTO;
import com.test.charity_api.dto.LecturerResponse;
import java.util.List;

public interface LecturerService {
    public List<LecturerDTO> getAll();

    public LecturerDTO findById(int id);

    public void createLecturer(LecturerDTO l);
    
    public void updateLecturer(LecturerDTO l);

    public void deleteLecturer(int id);

    public LecturerResponse getLecturers(int pageNo, int pageSize, String query);
    
    public boolean lecturerDoesntHaveAnyCampaign(int id);
}
