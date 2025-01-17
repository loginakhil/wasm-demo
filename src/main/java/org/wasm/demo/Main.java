package org.wasm.demo;

import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Memory;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.runtime.Instance;


public class Main {
    public static void main(String[] args) {
        var wasmHostExecutor = new WasmHostExecutor();
        var imports = ImportValues.builder().addFunction(wasmHostExecutor.toHostFunctions()).build();
        var wasmModule = Parser.parse(Main.class.getResourceAsStream("/plugin.wasm"));
        Instance instance =  Instance.builder(wasmModule).withImportValues(imports).build();

        // Call the exported function
        ExportFunction alloc = instance.export("malloc");
        ExportFunction dealloc = instance.export("free");
        Memory memory = instance.memory();
        String globalEntityId = "YS_TR";
        var globalEntityIdParamLen = globalEntityId.getBytes().length;
        var globalEntityIdPtr =  alloc.apply(globalEntityIdParamLen)[0];
        memory.writeString((int) globalEntityIdPtr, globalEntityId);

        ExportFunction add = instance.export("RoundPrices");
        var result =  Double.longBitsToDouble(add.apply(globalEntityIdPtr, globalEntityIdParamLen, Double.doubleToLongBits(5), Double.doubleToLongBits(3))[0]);
        System.out.println("Result of RoundPrices(YS_TR, 5, 3): " + result);
        dealloc.apply(globalEntityIdPtr);
    }
}
