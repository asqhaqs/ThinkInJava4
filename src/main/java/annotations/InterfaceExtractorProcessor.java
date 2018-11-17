package annotations;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by xudong on 2018/6/22.
 * p630
 */
public class InterfaceExtractorProcessor implements AnnotationProcessor {
    private final AnnotationProcessorEnvironment env;
    private ArrayList<MethodDeclaration> interfaceMethods = new ArrayList<MethodDeclaration>();
    public InterfaceExtractorProcessor (AnnotationProcessorEnvironment env){
        this.env = env;
    }
    @Override
    public void process(){
        for(TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()){
            ExtractInterface annot = typeDecl.getAnnotation(ExtractInterface.class);
            if(annot == null) break;
            for(MethodDeclaration m : typeDecl.getMethods()){
                if(m.getModifiers().contains(Modifier.PUBLIC) &&
                        !m.getModifiers().contains(Modifier.STATIC)){
                    interfaceMethods.add(m);
                }
            }
            if(interfaceMethods.size() > 0){
                try {
                    PrintWriter writer = env.getFiler().createSourceFile(annot.value());
                    writer.println("package " + typeDecl.getPackage().getQualifiedName() + ";");
                    writer.println("public interface " + annot.value() + " {");
                    for (MethodDeclaration m : interfaceMethods) {
                        writer.print(" public ");
                        writer.print(m.getReturnType() + " ");
                        writer.print(m.getSimpleName() + " (");
                        int i = 0;
                        for (ParameterDeclaration parm : m.getParameters()) {
                            writer.print(parm.getType() + " " + parm.getSimpleName());
                            if (++i < m.getParameters().size()) {
                                writer.print(", ");
                            }
                        }
                        writer.println(");");
                    }
                    writer.println("}");
                    writer.close();
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }

        }
    }

}
