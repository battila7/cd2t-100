package hu.progtech.cd2t100.computation;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.io.InputStream;
import java.io.IOException;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilationFailedException;

import org.apache.commons.io.IOUtils;

import hu.progtech.cd2t100.computation.annotations.*;

class InstructionLoader {
  public static InstructionInfo loadInstruction(InputStream codeStream)
    throws IOException, CompilationFailedException  {
    String code = IOUtils.toString(codeStream, "UTF-8");

    return loadInstruction(code);
  }

  public static InstructionInfo loadInstruction(String classCode)
    throws CompilationFailedException {
    GroovyClassLoader groovyClassLoader
      = new GroovyClassLoader(InstructionLoader.class.getClassLoader());

    Class<?> instructionClass = groovyClassLoader.parseClass(classCode);

      

    return null;
  }
}
