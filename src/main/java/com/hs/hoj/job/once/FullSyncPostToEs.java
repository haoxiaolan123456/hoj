package com.hs.hoj.job.once;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hs.hoj.esdao.PostEsDao;
import com.hs.hoj.model.dto.question.PostEsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;

/**
 * 全量同步帖子到 es
 */
// todo 取消注释开启任务
//@Component
/*@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {
        if (CollectionUtils.isEmpty(postList)) {
            return;
        }
        List<PostEsDTO> postEsDTOList = postList.stream().map(PostEsDTO::objToDto).collect(Collectors.toList());
        final int pageSize = 500;
        int total = postEsDTOList.size();
        log.info("FullSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            postEsDao.saveAll(postEsDTOList.subList(i, end));
        }
        log.info("FullSyncPostToEs end, total {}", total);
    }
}*/
