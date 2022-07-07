package org.imp.jvm.types;

import org.imp.jvm.Util;
import org.imp.jvm.parser.Expr;
import org.javatuples.Pair;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncType extends StructType {
    public final Map<String, Integer> localMap = new HashMap<>();
    public final Map<String, Integer> argMap = new HashMap<>();
    public String name;
    public ImpType returnType = BuiltInType.VOID;
    public GeneratorAdapter ga = null;
    public boolean glue = false;
    public boolean hasReturn2 = false;
    public boolean isPrefixed = false;
    public String owner;

    // Todo: will have to be List<List<ImpType>> to support multiple generics
    //  We'll probably need a standard sorting order for this.
    public List<Map<String, ImpType>> specializations = new ArrayList<>();

    public Map<String, ImpType> currentSpecialization = null;

    public FuncType(String name, List<Pair<String, ImpType>> parameters) {
        super(name, parameters, new ArrayList<>());
        this.name = name;

    }

    ;


    public FuncType(String name, List<Pair<String, ImpType>> parameters, List<String> generics) {
        super(name, parameters, generics);
        this.name = name;

    }

    public static ImpType getSpecializedType(Map<String, ImpType> specialization, String key) {
        return specialization.get(key);
    }

    public List<Pair<String, ImpType>> buildParametersFromSpecialization(Map<String, ImpType> specialization) {
        var p = new ArrayList<Pair<String, ImpType>>();
        for (int i = 0; i < parameters.size(); i++) {
            var pair = parameters.get(i);
            var pt = pair.getValue1();
            if (pt instanceof GenericType gt) {
                p.add(pair.setAt1(specialization.get(gt.key())));
            } else {
                p.add(pair);
            }
        }
        return p;
    }

    public Map<String, ImpType> buildSpecialization(List<Expr> arguments) {
        var argTypes = arguments.stream().map(ex -> ex.realType).toList();
        var specialization = new HashMap<String, ImpType>();
        for (int i = 0; i < parameters.size(); i++) {
            var p = parameters.get(i);
            var pt = p.getValue1();
            if (pt instanceof GenericType gt) {
                specialization.put(gt.key(), argTypes.get(i));
            }
        }
        return specialization;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return null;
    }

    @Override
    public String getInternalName() {
        return null;
    }

    @Override
    public int getLoadVariableOpcode() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getReturnOpcode() {
        return 0;
    }

    @Override
    public Class<?> getTypeClass() {
        return null;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public String kind() {
        return "function";
    }

    @Override
    public String toString() {
        return "func " + name + "(" + Util.parameterString(parameters) + ") " + returnType;
    }
}
