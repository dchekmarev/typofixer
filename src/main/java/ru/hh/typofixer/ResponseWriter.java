package ru.hh.typofixer;

import com.google.common.base.Function;
import java.util.Map;

public interface ResponseWriter extends Function<Map<? extends Token, Integer>, String> {
}
