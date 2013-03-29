package ru.hh.typofixer;

import com.google.common.base.Function;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Sets.newHashSet;
import ru.hh.typofixer.morphers.DoubleChars;
import ru.hh.typofixer.morphers.NoMorph;
import ru.hh.typofixer.morphers.SplitWords;
import ru.hh.typofixer.morphers.WrongKeyTable;
import java.util.Set;

public class Morpher extends Morph {
  private final Set<Morph> morphs = newHashSet();
  private final Set<Morph> pipes = newHashSet();

  {
    add(new NoMorph());
  }

  public static Morpher create() {
    return new Morpher();
  }

  public Morpher add(Morph morph) {
    morphs.add(morph);
    return this;
  }

  public Morpher pipe(Morph morph) {
    pipes.add(morph);
    return this;
  }

  @Override
  public Token apply(final Word source) {
    Iterable<Token> morphed = concat(transform(morphs, morphToken(source)));
    if (pipes.size() == 0) {
      return new Variant(morphed);
    }
    return new Variant(transform(morphed, new Function<Token, Token>() {
      @Override
      public Token apply(Token token) {
        return new Variant(transform(pipes, morphToken(token)));
      }
    }));
  }

  private static Function<Morph, Token> morphToken(final Token source) {
    return new Function<Morph, Token>() {
      @Override
      public Token apply(Morph morph) {
        return morph.apply(source);
      }
    };
  }

  public static Morpher configured() {
    return Morpher.create()
        .add(new NoMorph())
        .add(new SplitWords())
        .pipe(Morpher.create()
            .add(new NoMorph())
            .add(new DoubleChars())
            .add(new WrongKeyTable())
        );
  }
}
