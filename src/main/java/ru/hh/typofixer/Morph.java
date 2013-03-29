package ru.hh.typofixer;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

abstract public class Morph {
  public Token apply(Token token) {
    if (token instanceof Word) {
      return apply((Word) token);
    } else if (token instanceof Phrase) {
      return apply((Phrase) token);
    } else if (token instanceof Variant) {
      return apply((Variant) token);
    } else {
      throw new IllegalArgumentException(token.getClass().getCanonicalName());
    }
  }

  public Token apply(Word token) {
    return token;
  }

  public Variant apply(Variant token) {
    return new Variant(apply(token.getValues()));
  }

  public Phrase apply(Phrase token) {
    return new Phrase(apply(token.getValues()));
  }

  public Iterable<? extends Token> apply(Iterable<? extends  Token> tokens) {
    return Iterables.concat(
       Iterables.transform(tokens,
          new Function<Token, Token>() {
            @Override
            public Token apply(Token input) {
              return Morph.this.apply(input);
            }
          }));
  }
}
