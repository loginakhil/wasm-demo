package org.wasm.demo;

import com.dylibso.chicory.experimental.hostmodule.annotations.HostModule;
import com.dylibso.chicory.experimental.hostmodule.annotations.WasmExport;
import com.dylibso.chicory.log.SystemLogger;
import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.Memory;
import com.dylibso.chicory.wasi.WasiPreview1;

@HostModule("wasi_snapshot_preview1")
public final class WasmHostExecutor {
    private final WasiPreview1 wasiPreview1;

    public WasmHostExecutor() {
        this.wasiPreview1 = WasiPreview1.builder().withLogger(new SystemLogger()).build();
    };

    @WasmExport
    public int fdWrite(Memory memory, int fd, int iovs, int iovsLen, int nwrittenPtr) {
        return this.wasiPreview1.fdWrite(memory, fd, iovs, iovsLen, nwrittenPtr);
    }

    @WasmExport
    public int randomGet(Memory memory, int buf, int bufLen) {
        return this.wasiPreview1.randomGet(memory, buf, bufLen);
    }

    @WasmExport
    public void procExit(int code) {}

    @WasmExport
    public int procRaise(int sig) {return 0;}

    public HostFunction[] toHostFunctions() {
        return WasmHostExecutor_ModuleFactory.toHostFunctions(this);
    }
}

