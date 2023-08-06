package com.yoi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Word;

/**
 * @author 游弋
 * @create 2023-08-05 18:32
 */
public interface WordService extends IService<Word> {
    Boolean addWord(Word word);
    Boolean deleteWord(Long wordId);
    Word getById(Long wordId);
    Boolean updateWord(Word word);
}
