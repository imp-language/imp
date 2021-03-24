package imp.domain.root;

import imp.domain.scope.Method;
import imp.domain.scope.Property;

import java.util.List;

public class ClassUnit extends RootUnit {

    final List<Property> properties;
    final List<Method> methods;

    public ClassUnit(String name, List<Property> properties, List<Method> methods) {
        super(name);
        this.properties = properties;
        this.methods = methods;
    }
}
