package com.sp.trms.service;

import com.sp.trms.dao.ToolDao;
import com.sp.trms.domain.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to perform tool related functions i.e. retrieve a tool from the database etc.
 */
@Service
public class ToolService {
    private final ToolDao toolDao;

    public ToolService(@Autowired ToolDao toolDao) {
        this.toolDao = toolDao;
    }

    /**
     * Retrieves tool for a given tool code.
     * @param code - Tool Code
     * @return - Tool Object
     */
    public Tool getTool(String code) {
        return toolDao.getTool(code);
    }
}
