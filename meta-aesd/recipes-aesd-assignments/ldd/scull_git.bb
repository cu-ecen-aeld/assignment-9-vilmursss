inherit update-rc.d

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-vilmursss.git;protocol=ssh;branch=master \
           file://S98lddmodules"

PV = "1.0+git${SRCPV}"

SRCREV = "58a55a62fce5840dfb5dd3d80caa4980ac875b1e"

S = "${WORKDIR}/git/scull"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98lddmodules"
INITSCRIPT_PARAMS = "defaults 98"

inherit module

# Append to EXTRA_OEMAKE for the do_compile task
EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

FILES:${PN} += "${bindir}/scull_load ${bindir}/scull_unload ${sysconfdir}/init.d/S98lddmodules"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/scull_load ${D}${bindir}/scull_load
    install -m 0755 ${S}/scull_unload ${D}${bindir}/scull_unload
    
    # Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/S98lddmodules ${D}${sysconfdir}/init.d/S98lddmodules
    
    # Install device driver
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
}
