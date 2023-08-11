package com.yoi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Word;
import com.yoi.mapper.WordMapper;
import com.yoi.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 游弋
 * @create 2023-08-05 18:31
 */
@Service
@RequiredArgsConstructor
public class WordImpl extends ServiceImpl<WordMapper, Word> implements WordService {
    private final WordMapper wordMapper;

    @Override
    public Boolean addWord(Word word) {
        return wordMapper.insert(word)>0;
    }

    @Override
    public void deleteWord(Long wordId) {
        wordMapper.deleteById(wordId);
    }

    @Override
    public Word getById(Long wordId) {
        return wordMapper.selectById(wordId);
    }

    @Override
    public Boolean updateWord(Word word) {
        return wordMapper.updateById(word)>0;
    }
}
