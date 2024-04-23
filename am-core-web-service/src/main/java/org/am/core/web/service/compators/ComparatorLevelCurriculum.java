package org.am.core.web.service.compators;

import org.am.core.web.dto.admingeneral.LevelDto;

import java.util.Comparator;

public class ComparatorLevelCurriculum implements Comparator<LevelDto> {
    @Override
    public int compare(LevelDto o1, LevelDto o2) {
        return o1.levelIdentifier() - o2.levelIdentifier();
    }
}
